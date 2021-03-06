package com.saintdan.framework.po

import com.fasterxml.jackson.annotation.JsonIgnore
import com.saintdan.framework.listener.UpdateListener
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import javax.persistence.*

/**
 * Authorized resources, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 16/12/2017
 * @since JDK1.8
 */
@Entity
@Table(name = "resources")
@EntityListeners(UpdateListener::class)
data class Resource(

    @GenericGenerator(name = "resourceSequenceGenerator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = [
          Parameter(name = "sequence_name", value = "resources_seq"),
          Parameter(name = "initial_value", value = "1"),
          Parameter(name = "increment_size", value = "1")])
    @Id
    @GeneratedValue(generator = "resourceSequenceGenerator")
    @Column(updatable = false)
    val id: Long = 0,

    @Column(unique = true, nullable = false, length = 20)
    val name: String = "",

    @Column(length = 500)
    val description: String? = null,

    @Column(nullable = false, updatable = false)
    val createdAt: Long = System.currentTimeMillis(),

    @Column(nullable = false, updatable = false)
    @JsonIgnore
    val createdBy: Long = 0,

    @Column(nullable = false)
    @JsonIgnore
    var lastModifiedAt: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    @JsonIgnore
    var lastModifiedBy: Long = 0,

    @Version
    @Column(nullable = false)
    @JsonIgnore
    var version: Int = 0,

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources", cascade = [CascadeType.REFRESH])
    @JsonIgnore
    val roles: Set<Role> = emptySet()
) : GrantedAuthority, Serializable {
  companion object {
    private const val serialVersionUID = 6298843159549723556L
  }

  override fun getAuthority(): String = name

  override fun equals(other: Any?): Boolean {
    if (this === other) return true

    if (other == null || javaClass != other.javaClass) return false

    val role = other as Role?

    return EqualsBuilder()
        .appendSuper(super.equals(other))
        .append(id, role!!.id)
        .append(createdAt, role.createdAt)
        .append(createdBy, role.createdBy)
        .append(lastModifiedAt, role.lastModifiedAt)
        .append(lastModifiedBy, role.lastModifiedBy)
        .append(version, role.version)
        .append(name, role.name)
        .append(description, role.description)
        .isEquals
  }

  override fun hashCode(): Int {
    return HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(id)
        .append(name)
        .append(description)
        .append(createdAt)
        .append(createdBy)
        .append(lastModifiedAt)
        .append(lastModifiedBy)
        .append(version)
        .toHashCode()
  }

  override fun toString(): String =
      StringBuilder("Resource(")
          .append("id = ").append(id)
          .append(", name = ").append(name)
          .append(", description = ").append(description)
          .append(", createdAt = ").append(createdAt)
          .append(", createdBy = ").append(createdBy)
          .append(", lastModifiedAt = ").append(lastModifiedAt)
          .append(", lastModifiedBy = ").append(lastModifiedBy)
          .append(", version = ").append(version)
          .append(")")
          .toString()
}