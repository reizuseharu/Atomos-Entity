package com.reizu.core.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.reizu.core.util.DateTimeFormatter
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.EntityNotFoundException
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 * Abstract class for the base of an entity
 *
 * @property id Unique identifier for entity
 * @property createdOn When entity's entry was created
 * @property modifiedOn When entity's entry was last modified
 * @property removedOn When entity's entry was removed
 */
@MappedSuperclass
abstract class BaseUniqueEntity : UniqueEntityable {

    @JsonIgnore
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Type(type = "pg-uuid")
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    override var _id: UUID? = null

    override val id: UUID
        get() {
            if (_id == null) {
                throw EntityNotFoundException()
            }
            return _id as UUID
        }

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @CreatedDate
    @CreationTimestamp
    @Column(name = "createdOn", updatable = false)
    override var createdOn: LocalDateTime? = null

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "modifiedOn")
    override var modifiedOn: LocalDateTime? = null

    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @DateTimeFormat(pattern = DateTimeFormatter.DATE_TIME_PATTERN)
    @Column(name = "removedOn")
    override var removedOn: LocalDateTime? = null

}
