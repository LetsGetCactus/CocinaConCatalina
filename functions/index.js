const functions = require('firebase-functions');
const admin = require('firebase-admin');
const fs = require('fs');
const {Translate} = require('@google-cloud/translate').v2;
require('dotenv').config();

const KEY = process.env.DB_KEY_PATH;
const serviceAccount = JSON.parse(fs.readFileSync(KEY, 'utf8'));

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

const db = admin.firestore();
const translate = new Translate({projectId: process.env.GOOGLE_PROJECT_ID});

const LANGUAGES = ['es', 'gl', 'en'];

/**
 * Funciones de traducción
 * @param {String} texto
 * @param {String} idioma
 */
async function traducirTexto(texto, idioma) {
  if (!texto || idioma === 'es') return texto;
  try {
    const [translation] = await translate.translate(texto, idioma);
    return translation;
  } catch (error) {
    console.error(`Error traduciendo "${texto}" a ${idioma}:`, error);
    return texto;
  }
}

/**
 * Traduce un texto
 * @param {String} textoOriginal
 */
async function crearMultilenguaje(textoOriginal) {
  const obj = {};
  for (const lang of LANGUAGES) {
    obj[lang] = await traducirTexto(textoOriginal, lang);
  }
  return obj;
}

/**
 * Conviertes RecipeDto a formato multilanguage
 * @param {Object} recipeDto
 */
async function convertirRecipeDtoMultilenguaje(recipeDto) {
  const ingredientList = await Promise.all(
      recipeDto.ingredientList.map(async (ing) => ({
        name: await crearMultilenguaje(ing.name['es'] || ''),
        quantity: ing.quantity,
        unit: ing.unit,
      })),
  );

  const allergenList = await Promise.all(
      recipeDto.allergenList.map(async (all) => ({
        name: await crearMultilenguaje(all.name['es'] || ''),
        img: all.img,
      })),
  );

  const categoryList = await Promise.all(
      recipeDto.categoryList.map(async (cat) => ({
        id: cat.id,
        name: await crearMultilenguaje(cat.name['es'] || ''),
      })),
  );

  const steps = await Promise.all(
      recipeDto.steps.map(async (stepMap) =>
        crearMultilenguaje(stepMap['es'] || '')),
  );

  return {
    id: recipeDto.id,
    title: await crearMultilenguaje(recipeDto.title['es'] || ''),
    avgRating: recipeDto.avgRating,
    steps: steps,
    ingredientList: ingredientList,
    allergenList: allergenList,
    categoryList: categoryList,
    prepTime: recipeDto.prepTime,
    dificulty: recipeDto.dificulty,
    origin: recipeDto.origin,
    portions: recipeDto.portions,
    active: recipeDto.active,
    img: recipeDto.img,
    video: recipeDto.video || '',
  };
}

// Cloud Functions
exports.uploadOriginalRecipe = functions
    .https.onCall(async (data, context) => {
      try {
        const multilanguageRecipe = await convertirRecipeDtoMultilenguaje(data);
        await db.collection('asianOriginalRecipes')
            .doc(multilanguageRecipe.id)
            .set(multilanguageRecipe);
        return {success: true, message: 'Receta Og subida correctamente.'};
      } catch (error) {
        console.error('Error subiendo receta original:', error);
        return {success: false, message: error.message};
      }
    });

exports.uploadModifiedRecipe = functions
    .https.onCall(async (data, context) => {
      try {
        if (!context.auth) throw new Error('Usuario no autenticado');
        const userId = context.auth.uid;
        const multilanguageRecipe = await convertirRecipeDtoMultilenguaje(data);
        await db.collection('users')
            .doc(userId)
            .collection('modifiedRecipes')
            .doc(multilanguageRecipe.id)
            .set(multilanguageRecipe);
        return {success: true, message: 'Receta mod subida correctamente'};
      } catch (error) {
        console.error('Error subiendo receta modificada:', error);
        return {success: false, message: error.message};
      }
    });
