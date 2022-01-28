/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.gradle.generator.js

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import dev.icerock.gradle.generator.ImagesGenerator
import dev.icerock.gradle.generator.js_jvm_common.generateHighestQualityImageResources
import dev.icerock.gradle.generator.jvm.JvmImagesGenerator
import org.gradle.api.file.FileTree
import java.io.File

class JsImagesGenerator(
    inputFileTree: FileTree
) : ImagesGenerator(inputFileTree) {
    override fun getClassModifiers(): Array<KModifier> = arrayOf(KModifier.ACTUAL)

    override fun getPropertyModifiers(): Array<KModifier> = arrayOf(KModifier.ACTUAL)

    override fun getPropertyInitializer(fileName: String): CodeBlock {
        return CodeBlock.of("ImageResource(fileUrl = js(\"require(\\\"$IMAGES_DIR/$fileName\\\")\") as String)")
    }

    override fun generateResources(resourcesGenerationDir: File, keyFileMap: Map<String, List<File>>) {
        generateHighestQualityImageResources(resourcesGenerationDir, keyFileMap, IMAGES_DIR)
    }

    override fun extendObjectBodyAtStart(classBuilder: TypeSpec.Builder) = Unit

    override fun extendObjectBodyAtEnd(classBuilder: TypeSpec.Builder) = Unit

    companion object {
        const val IMAGES_DIR = "images"
    }
}