import com.intellij.codeInsight.lookup.LookupEvent
import com.intellij.codeInsight.lookup.LookupListener

class CopilotLookupListener : LookupListener {
    override fun itemSelected(event: LookupEvent) {
        event.item?.lookupString?.let { handleCompletion(it) }
    }

    override fun lookupCanceled(event: LookupEvent) {
        // No action needed on cancel
    }

    private fun handleCompletion(completionText: String) {
        if (isLikelyCopilotCompletion(completionText)) {
            CopilotCompletionContributor.addCompletion(completionText)

            // Log or handle the completion based on its type
            if (isFunctionGeneration(completionText)) {
                println("Copilot used for function generation: $completionText")
            } else {
                println("Copilot used for auto-complete: $completionText")
            }
        }
    }

    private fun isLikelyCopilotCompletion(text: String): Boolean {
        // Check for patterns typical of Copilot completions
        return text.contains("function") || text.contains("def") || text.contains("class") ||
                text.matches(Regex(".*(def\\s+\\w+\\s*\\(.*\\):).*")) ||
                text.matches(Regex(".*(public|private|protected)?\\s*(static)?\\s*\\w+\\s+\\w+\\s*\\(.*\\)\\s*\\{.*")) ||
                text.length > 50 || text.contains("{") || text.contains("}")
    }

    private fun isFunctionGeneration(text: String): Boolean {
        return text.contains("function") || text.contains("def") || text.contains("class") ||
                text.matches(Regex(".*(def\\s+\\w+\\s*\\(.*\\):).*")) ||
                text.matches(Regex(".*(public|private|protected)?\\s*(static)?\\s*\\w+\\s+\\w+\\s*\\(.*\\)\\s*\\{.*")) ||
                text.length > 50 || text.contains("{") || text.contains("}")
    }
}
