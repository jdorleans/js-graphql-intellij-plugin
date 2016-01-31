/**
 *  Copyright (c) 2015-present, Jim Kynde Meyer
 *  All rights reserved.
 *
 *  This source code is licensed under the MIT license found in the
 *  LICENSE file in the root directory of this source tree.
 */
package com.intellij.lang.jsgraphql;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lang.jsgraphql.lexer.JSGraphQLLexer;
import com.intellij.lang.jsgraphql.parser.JSGraphQLParser;
import com.intellij.lang.jsgraphql.psi.JSGraphQLFile;
import com.intellij.lang.jsgraphql.psi.JSGraphQLPsiFactory;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class JSGraphQLParserDefinition implements ParserDefinition{

    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(JSGraphQLTokenTypes.COMMENT);
    public static final TokenSet STRINGS = TokenSet.create(JSGraphQLTokenTypes.STRING);

    public static final IFileElementType FILE = new IFileElementType(Language.findInstance(JSGraphQLLanguage.class));

    public static final Key<Boolean> JSGRAPHQL_ACTIVATED = Key.create("JSGraphQL.activated");

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new JSGraphQLLexer(project);
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return STRINGS;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        if(project != null) {
            project.putUserData(JSGRAPHQL_ACTIVATED, true);
        }
        return new JSGraphQLParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new JSGraphQLFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return JSGraphQLPsiFactory.createElement(node);
    }
}