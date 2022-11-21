/*
  Copyright 2019 Kakao Corp.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
apply(plugin = "com.android.library")

// 모든 라이브러리들이 공통으로 import 하는 라이브러리는 테스트 라이브러리 밖에 없다. 테스트 라이브러리는 써드 파티 앱에 포함이 되지 않으니까 공통으로 지정해도 큰 이슈 없음.
dependencies {
    "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.1.1")
    "testImplementation"("org.junit.jupiter:junit-jupiter-params:5.1.1")
    "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:5.1.1")

    "testRuntimeOnly"("org.junit.vintage:junit-vintage-engine:5.1.1")
    "testImplementation"("org.robolectric:robolectric:4.7.3")
    "testImplementation"(Dependencies.mockwebserver)
    "testImplementation"(Dependencies.mockitoCore)
    "testImplementation"(Dependencies.mockitoInline)
    "testImplementation"("androidx.test:core:1.4.0")
    "testImplementation"("androidx.test:core-ktx:1.4.0")
    "testImplementation"("androidx.test:runner:1.2.0")
    "testImplementation"("androidx.test.ext:junit:1.1.1")
    "testImplementation"("androidx.arch.core:core-testing:2.1.0")

    "androidTestImplementation"(Dependencies.mockwebserver)
    "androidTestImplementation"(Dependencies.mockitoCore)
    "androidTestImplementation"(Dependencies.mockitoInline)
    "androidTestImplementation"("androidx.test:core:1.4.0")
    "androidTestImplementation"("androidx.test:core-ktx:1.4.0")
    "androidTestImplementation"("androidx.test:runner:1.2.0")
    "androidTestImplementation"("androidx.test.ext:junit:1.1.1")
    "androidTestImplementation"("androidx.test.espresso:espresso-core:3.4.0")
    "androidTestImplementation"("androidx.test.espresso:espresso-contrib:3.4.0")
    "androidTestImplementation"("androidx.arch.core:core-testing:2.1.0")

    "kaptTest"("com.google.auto.service:auto-service:1.0-rc4")
    "testAnnotationProcessor"("com.google.auto.service:auto-service:1.0-rc4")
}