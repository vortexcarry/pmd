/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.lang.apex.rule;

import java.util.List;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.ParserOptions;
import net.sourceforge.pmd.lang.apex.ApexLanguageModule;
import net.sourceforge.pmd.lang.apex.ApexParserOptions;
import net.sourceforge.pmd.lang.apex.ast.ASTMethod;
import net.sourceforge.pmd.lang.apex.ast.ASTUserClass;
import net.sourceforge.pmd.lang.apex.ast.ApexNode;
import net.sourceforge.pmd.lang.apex.ast.ApexParserVisitor;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.rule.AbstractRule;
import net.sourceforge.pmd.lang.rule.ImmutableLanguage;

public abstract class AbstractApexRule extends AbstractRule implements
        ApexParserVisitor, ImmutableLanguage {

    public AbstractApexRule() {
        super.setLanguage(LanguageRegistry.getLanguage(ApexLanguageModule.NAME));
    }

    @Override
    public ParserOptions getParserOptions() {
        return new ApexParserOptions();
    }

    public void apply(List<? extends Node> nodes, RuleContext ctx) {
        visitAll(nodes, ctx);
    }

    protected void visitAll(List<? extends Node> nodes, RuleContext ctx) {
        for (Object element : nodes) {
            ASTUserClass node = (ASTUserClass) element;
            visit(node, ctx);
        }
    }

    //
    // The following APIs are identical to those in ApexParserVisitorAdapter.
    // Due to Java single inheritance, it preferred to extend from the more
    // complex Rule base class instead of from relatively simple Visitor.
    //

    public Object visit(ApexNode<?> node, Object data) {
        node.childrenAccept(this, data);
        return null;
    }

    public Object visit(ASTMethod node, Object data) {
        return visit((ApexNode<?>) node, data);
    }

    public Object visit(ASTUserClass node, Object data) {
        return visit((ApexNode<?>) node, data);
    }
}