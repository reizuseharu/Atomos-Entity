package com.reizu.core.entity

/**
 * Interface specifying required field to be identifiable
 *
 * @property id Unique identifier for entity
 */
interface SerialIdentifiable : Identifiable {

    var _id: Long?

    val id: Long

}
