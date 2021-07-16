package com.filippoengidashet.petsapp.data

/**
 * @author Filippo 15/07/2021
 */

data class Breed(
    val weight: Weight,
    val id: String? = null,
    val name: String? = null,
    val cfa_url: String? = null,
    val vetstreet_url: String? = null,
    val vcahospitals_url: String? = null,
    val temperament: String? = null,
    val origin: String? = null,
    val country_codes: String? = null,
    val country_code: String? = null,
    val description: String? = null,
    val life_span: String? = null,
    val indoor: Float? = null,
    val lap: Float? = null,
    val alt_names: String? = null,
    val adaptability: Float? = null,
    val affection_level: Float? = null,
    val child_friendly: Float? = null,
    val dog_friendly: Float? = null,
    val energy_level: Float? = null,
    val grooming: Float? = null,
    val health_issues: Float? = null,
    val intelligence: Float? = null,
    val shedding_level: Float? = null,
    val social_needs: Float? = null,
    val stranger_friendly: Float? = null,
    val vocalisation: Float? = null,
    val experimental: Float? = null,
    val hairless: Float? = null,
    val natural: Float? = null,
    val rare: Float? = null,
    val rex: Float? = null,
    val suppressed_tail: Float? = null,
    val short_legs: Float? = null,
    val wikipedia_url: String? = null,
    val hypoallergenic: Float? = null,
    val reference_image_id: String? = null,
    val image: Image?
)

data class Weight(val imperial: String?, val metric: String?)
data class Image(val id: String?, val width: Int, val height: Int, val url: String?)

data class BreedSearch(
    val breeds: List<Breed>,
    val id: String? = null,
    val url: String? = null,
    val width: Int? = null,
    val height: Int? = null
)
