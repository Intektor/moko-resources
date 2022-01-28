/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.gradle.generator.js

import com.squareup.kotlinpoet.*
import dev.icerock.gradle.generator.MRGenerator
import org.gradle.api.Project
import org.gradle.api.Task
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrCompilation
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

class JsMRGenerator(
    generatedDir: File,
    sourceSet: SourceSet,
    mrSettings: MRSettings,
    generators: List<Generator>
) : MRGenerator(
    generatedDir = generatedDir,
    sourceSet = sourceSet,
    mrSettings = mrSettings,
    generators = generators
) {

    override val resourcesGenerationDir: File
        get() = outputDir.resolve("resources")

    override fun getMRClassModifiers(): Array<KModifier> = arrayOf(KModifier.ACTUAL)

    override fun processMRClass(mrClass: TypeSpec.Builder) {
    }

    override fun apply(generationTask: Task, project: Project) {
        project.tasks.apply {
            withType(Kotlin2JsCompile::class.java).all {
                it.dependsOn(generationTask)
            }
        }
    }

    companion object {
        const val STRINGS_JSON_NAME = "stringsJson"
        const val PLURALS_JSON_NAME = "pluralsJson"

        const val SUPPORTED_LOCALES_PROPERTY_NAME = "supportedLocales"
        const val STRINGS_FALLBACK_FILE_URL_PROPERTY_NAME = "stringsFallbackFileUrl"
        const val PLURALS_FALLBACK_FILE_URL_PROPERTY_NAME = "stringsFallbackFileUrl"
        const val LOCALIZATION_DIR = "localization"
    }
}