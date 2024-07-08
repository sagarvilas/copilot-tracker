package com.github.sagarvilas.copilottracker.startup

import CopilotLookupListener
import CopilotUsageListener
import com.intellij.codeInsight.lookup.LookupManager
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class CopilotActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        val listener = CopilotLookupListener()
        LookupManager.getInstance(project)?.activeLookup?.addLookupListener(listener)
        EditorFactory.getInstance().eventMulticaster.addDocumentListener(CopilotUsageListener(), project)    }
}
