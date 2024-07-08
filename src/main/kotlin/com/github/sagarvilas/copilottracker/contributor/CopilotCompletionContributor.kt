import com.intellij.codeInsight.completion.*
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import java.util.*

class CopilotCompletionContributor : CompletionContributor() {

    companion object {
        private const val COMPLETION_CACHE_SIZE = 10 // Limit the size of the cache
        private val recentCompletions: Queue<String> = LinkedList()

        fun getRecentCompletions(): Queue<String> {
            return recentCompletions
        }

        fun addCompletion(text: String) {
            if (recentCompletions.size >= COMPLETION_CACHE_SIZE) {
                recentCompletions.poll() // Remove the oldest entry if the cache is full
            }
            recentCompletions.add(text)
        }
    }

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    result.runRemainingContributors(parameters) { completionResult ->
                        val element = completionResult.lookupElement
                        result.addElement(element)
                    }
                }
            }
        )
    }
}
