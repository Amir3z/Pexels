package com.amirez.pexels.utils

import com.amirez.pexels.R
import com.amirez.pexels.model.CollectionsData

const val API_KEY = "563492ad6f91700001000001798afed1ba2b45789fa55389a7f6dfbc"
const val PEXELS_BASE_URL = "https://api.pexels.com/v1/"

const val CAR_COLLECTION_ID = "dvgfira"
const val SPACE_COLLECTION_ID = "vsjdlqy"
const val ANIMAL_COLLECTION_ID = "h8stntd"
const val PASTEL_COLLECTION_ID = "o20xwnm"
const val TULIP_COLLECTION_ID = "b5sgybo"
const val ART_COLLECTION_ID = "otnofqv"
const val TECHNOLOGY_COLLECTION_ID = "p4wlu1p"
const val NATURE_COLLECTION_ID = "zlezwqr"
const val CODING_COLLECTION_ID = "vhkuffy"
const val COFFEE_COLLECTION_ID = "1zxneg8"

val collectionsData = listOf(
    CollectionsData("Cars", CAR_COLLECTION_ID, R.drawable.img_collection_cars),
    CollectionsData("Space", SPACE_COLLECTION_ID, R.drawable.img_collection_space),
    CollectionsData("Animals", ANIMAL_COLLECTION_ID, R.drawable.img_collection_animals),
    CollectionsData("Pastel Background", PASTEL_COLLECTION_ID, R.drawable.img_collection_pastel),
    CollectionsData("Tulips", TULIP_COLLECTION_ID, R.drawable.img_collection_tulips),
    CollectionsData("Art", ART_COLLECTION_ID, R.drawable.img_collection_art),
    CollectionsData("Technology", TECHNOLOGY_COLLECTION_ID, R.drawable.img_collection_technology),
    CollectionsData("Just Nature", NATURE_COLLECTION_ID, R.drawable.img_collection_nature),
    CollectionsData("Coding", CODING_COLLECTION_ID, R.drawable.img_collection_coding),
    CollectionsData("Coffee", COFFEE_COLLECTION_ID, R.drawable.img_collection_coffee)
)



