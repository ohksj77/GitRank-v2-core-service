package com.dragonguard.core.domain.gitrepo

import com.dragonguard.core.domain.member.Member
import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class GitRepo(
    @NaturalId
    val name: String
) : BaseEntity() {

    @CollectionTable
    @ElementCollection
    var sparkLine: MutableList<Int> = mutableListOf()
        protected set

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], mappedBy = "gitRepo")
    var gitRepoMembers: MutableList<GitRepoMember> = mutableListOf()
        protected set

    fun addMember(member: Member) {
        gitRepoMembers.add(GitRepoMember(this, member))
    }

    fun hasMember(): Boolean =
        gitRepoMembers.isNotEmpty()
}
