package fr.kommentaire.server.upvote

data class Upvote(val userId: Int, val questionId: Int, val upvoteType: UpvoteType)

enum class UpvoteType {
    DOWNVOTE,
    UPVOTE,
    NONE
}