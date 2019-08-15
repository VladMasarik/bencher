package ch.uzh.ifi.seal.bencher.analysis.finder.jdt

import ch.uzh.ifi.seal.bencher.analysis.finder.shared.StateObjectManager
import org.eclipse.jdt.core.dom.Annotation
import org.eclipse.jdt.core.dom.TypeDeclaration

class JdtBenchStateObjectVisitor(private val som: StateObjectManager) : JdtBenchAbstractClassVisitor(som) {
    private var isStateObject = false

    override fun visit(node: TypeDeclaration): Boolean {
        val res = super.visit(node)

        if (isStateObject) {
            val bf = fvs.map { it.benchField }
            som.add(fullyQualifiedClassName, bf)
        }

        return res
    }

    override fun visitAnnotation(node: Annotation) {
        val name = node.resolveTypeBinding().qualifiedName
        when (name) {
            JMHConstants.Annotation.state -> {
                isStateObject = true
            }
        }
    }
}