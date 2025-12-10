package com.google.samples.apps.nowinandroid

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class NiaFlavor(val dimension: FlavorDimension, val applicationIdSuffix: String? = null) {
    demo(FlavorDimension.contentType, applicationIdSuffix = ".demo"),
    prod(FlavorDimension.contentType),
}

fun configureFlavors(
    commonExtension: CommonExtension,
    flavorConfigurationBlock: ProductFlavor.(flavor: NiaFlavor) -> Unit = {},
) {
    when (commonExtension) {
        is ApplicationExtension -> {
            commonExtension.flavorDimensions += FlavorDimension.contentType.name
            commonExtension.productFlavors {
                NiaFlavor.values().forEach { niaFlavor ->
                    create(niaFlavor.name) {
                        dimension = niaFlavor.dimension.name
                        flavorConfigurationBlock(this, niaFlavor)
                        if (niaFlavor.applicationIdSuffix != null) {
                            applicationIdSuffix = niaFlavor.applicationIdSuffix
                        }
                    }
                }
            }
        }
        is LibraryExtension -> {
            commonExtension.flavorDimensions += FlavorDimension.contentType.name
            commonExtension.productFlavors {
                NiaFlavor.values().forEach { niaFlavor ->
                    create(niaFlavor.name) {
                        dimension = niaFlavor.dimension.name
                        flavorConfigurationBlock(this, niaFlavor)
                    }
                }
            }
        }
    }
}
