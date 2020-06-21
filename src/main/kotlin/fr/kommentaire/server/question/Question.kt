package fr.kommentaire.server.question

import com.expediagroup.graphql.annotations.GraphQLIgnore
import fr.kommentaire.server.BeanUtil
import fr.kommentaire.server.authentication.GraphQLHttpContext
import fr.kommentaire.server.upvote.Upvote
import fr.kommentaire.server.upvote.UpvoteRepository
import fr.kommentaire.server.upvote.UpvoteType
import fr.kommentaire.server.user.UserRepository

data class Question(val id: Int, val content: String, @GraphQLIgnore val userId: Int = 0) {

    @GraphQLIgnore
    private val userRepository = BeanUtil.getBean(UserRepository::class.java)

    @GraphQLIgnore
    private val upvoteRepository = BeanUtil.getBean(UpvoteRepository::class.java)

    fun votes() = upvoteRepository.findQuestionVotes(id).sumBy {
        if (it.upvoteType == UpvoteType.DOWNVOTE) {
            -1
        } else +1
    }

    fun upvotes() = upvoteRepository.findQuestionVotes(id).count {
        it.upvoteType == UpvoteType.UPVOTE
    }

    fun downvotes() = upvoteRepository.findQuestionVotes(id).count {
        it.upvoteType == UpvoteType.DOWNVOTE
    }

    fun user() = userRepository.findUserFromId(userId)

    fun isMine(graphQLHttpContext: GraphQLHttpContext) = graphQLHttpContext.user?.id == userId

    fun userVoteType(graphQLHttpContext: GraphQLHttpContext) : UpvoteType {
        var upvoteType: UpvoteType? = null
        if (graphQLHttpContext.user != null) {
            upvoteType = upvoteRepository.findQuestionUserVote(id, graphQLHttpContext.user!!.id)?.upvoteType
        }
        return upvoteType ?: UpvoteType.NONE
    }
}
