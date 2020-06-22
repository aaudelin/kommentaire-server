package fr.kommentaire.server

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date


@Component
class BeanUtil : ApplicationContextAware {
    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }

    companion object {
        private var context: ApplicationContext? = null
        fun <T> getBean(beanClass: Class<T>): T {
            return context!!.getBean(beanClass)
        }

        private var questionChangesId = mutableMapOf<Int, Date>()
        fun addQuestionId(questionId: Int?) = questionId?.let { questionChangesId.put(it, Date.from(Instant.now())) }
        fun deleteQuestionId(questionId: Int?) = questionId?.let { questionChangesId.remove(it) }
        fun getQuestions() = questionChangesId
    }
}