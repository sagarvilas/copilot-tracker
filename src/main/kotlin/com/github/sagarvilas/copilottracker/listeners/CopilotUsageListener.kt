import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener

class CopilotUsageListener : DocumentListener {
    override fun beforeDocumentChange(event: DocumentEvent) {
        // Logic before the document changes
    }

    override fun documentChanged(event: DocumentEvent) {
        val newText = event.newFragment.toString()
        if (isCopilotGenerated(newText)) {
            println("Copilot was likely used for this change: $newText")
        }
    }

    private fun isCopilotGenerated(newText: String): Boolean {
        val recentCompletions = CopilotCompletionContributor.getRecentCompletions()

        // Check if the new text matches any of the recent completions
        for (completion in recentCompletions) {
            if (newText.contains(completion)) {
                return true
            }
        }
        return false
    }
}
