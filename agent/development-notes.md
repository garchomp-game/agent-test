# Kotlin + Thymeleaf プロジェクト開発メモ

## プロジェクト作成経過

1. Spring Initializr を使用して、以下の設定でプロジェクトを作成しました:
   - 言語: Kotlin
   - ビルドツール: Gradle (Kotlin DSL)
   - 依存関係: Spring Web, Thymeleaf
   - Java バージョン: 21
   - パッケージ名: com.example.kotlin_thymeleaf_demo
   - プロジェクト名: kotlin-thymeleaf-demo

2. 基本的なコントローラーを追加しました:
   - `HomeController.kt`: ルートURLにアクセスしたときにhomeビューを表示するコントローラー

3. Thymeleafテンプレートを追加しました:
   - `home.html`: シンプルなウェルカムページのテンプレート

## 現在のプロジェクト構造

```
kotlin-thymeleaf-demo/
├── src/
│   ├── main/
│   │   ├── kotlin/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── kotlin_thymeleaf_demo/
│   │   │               ├── KotlinThymeleafDemoApplication.kt
│   │   │               └── controllers/
│   │   │                   └── HomeController.kt
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   │           └── home.html
│   └── test/
│       └── kotlin/
│           └── com/
│               └── example/
│                   └── kotlin_thymeleaf_demo/
│                       └── KotlinThymeleafDemoApplicationTests.kt
├── build.gradle.kts
├── gradle/
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
├── agent/
├── user/
└── docs/
```

## 技術スタック

- **言語**: Kotlin 1.9.25
- **フレームワーク**: Spring Boot 3.4.4
- **ビルドツール**: Gradle
- **テンプレートエンジン**: Thymeleaf
- **Java バージョン**: 21

## 実行方法

プロジェクトを実行するには、以下のコマンドを実行します:

```
./gradlew bootRun
```

実行後、ブラウザで http://localhost:8080 にアクセスすると、ホームページが表示されます。

## 次のステップ

- 要件定義や仕様設計をdocsフォルダに作成する
- モデル（エンティティ）の追加
- データアクセス層（リポジトリ）の実装
- サービス層の追加
- 追加のコントローラーとビューの作成
