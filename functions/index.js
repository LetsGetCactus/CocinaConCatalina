const functions = require("firebase-functions");
const admin = require("firebase-admin");
const {v2: Translate} = require("@google-cloud/translate");

admin.initializeApp();
const db = admin.firestore();
const translate = new Translate
    .Translate({projectId: "cocinaconcatarina-f6667"});

/**
 * Traduce un texto a un idioma objetivo.
 * @param {string} text - Texto a traducir
 * @param {string} target - Idioma destino ("es", "gl", "en")
 * @param {string} idiomaOriginal - Idioma original
 * @return {Promise<string>} Texto traducido
 */
async function traducir(text, target, idiomaOriginal) {
  if (target === idiomaOriginal) return text;
  const [translation] = await translate.translate(text, target);
  return translation;
}

/**
 * Función Cloud Function para traducir y guardar recetas en Firestore
 */
exports.traducirReceta = functions.https.onCall(async (data, context) => {
  try {
    const receta = data.receta; // receta enviada desde la app
    const idiomaOriginal = data.idiomaOriginal; // "es", "gl", "en"
    const idiomas = ["es", "gl", "en"];

    // Traducir título
    const titleMap = {};
    for (const lang of idiomas) {
      titleMap[lang] = await traducir(receta.title, lang, idiomaOriginal);
    }

    // Traducir pasos
    const stepsList = [];
    for (const step of receta.steps) {
      const stepMap = {};
      for (const lang of idiomas) {
        stepMap[lang] = await traducir(step, lang, idiomaOriginal);
      }
      stepsList.push(stepMap);
    }

    // Traducir ingredientes
    const ingredientList = [];
    for (const ing of receta.ingredientList) {
      const nameMap = {};
      for (const lang of idiomas) {
        nameMap[lang] = await traducir(ing.name, lang, idiomaOriginal);
      }
      ingredientList.push({
        ...ing,
        name: nameMap,
      });
    }

    // Construir la receta multilenguaje
    const recetaMultilang = {
      ...receta,
      title: titleMap,
      steps: stepsList,
      ingredientList: ingredientList,
    };

    // Guardar en Firestore
    await db.collection("asianOriginalRecipes")
        .doc(receta.id)
        .set(recetaMultilang);

    return {success: true, receta: recetaMultilang};
  } catch (error) {
    console.error(error);
    return {success: false, error: error.message};
  }
});
