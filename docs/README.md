# リアルタイムチャット機能付き掲示板 - 設計概要

## プロジェクト概要

このプロジェクトは、Kotlin + Spring Boot + Thymeleafを使用した掲示板システムとリアルタイムチャット機能を組み合わせたウェブアプリケーションです。ユーザーは投稿の作成・閲覧、コメント、チャットルームでのリアルタイムコミュニケーションが可能です。

## 設計ドキュメント構成

本プロジェクトの設計は以下のドキュメントで構成されています：

1. **要件定義**: [requirements.md](./requirements.md)
   - 機能要件、非機能要件、UI要件、MVPとロードマップなど

2. **アーキテクチャ設計**: [architecture/system-architecture.md](./architecture/system-architecture.md)
   - 全体アーキテクチャ、レイヤー構成、技術スタック、モジュール構成など

3. **データベース設計**: [database/database-design.md](./database/database-design.md)
   - ER図、テーブル定義、インデックス設計、初期データなど

4. **UI設計**: [ui/ui-design.md](./ui/ui-design.md)
   - 画面遷移図、主要画面レイアウト、レスポンシブデザイン、スタイルガイドなど

5. **開発ワークフロー**: [workflow/development-workflow.md](./workflow/development-workflow.md)
   - MVP実装計画、実装ガントチャート、エージェント向け実装ガイド、テスト戦略など

6. **API設計**: [api/api-design.md](./api/api-design.md)
   - RESTful APIエンドポイント、WebSocket API、レスポンス形式、認証・認可など

## MVP開発の最小実装セット

MVPの実装では、以下の機能を最小限の実装セットとして開発します：

### 1. ユーザー管理
- 基本的なユーザー登録・ログイン
- シンプルなプロフィール表示

### 2. 掲示板機能
- 投稿の作成と表示
- 基本的なカテゴリ分類

### 3. コメント機能
- 投稿に対するコメント投稿
- コメント一覧表示

### 4. チャット機能
- 単一の公開チャットルーム
- テキストメッセージのリアルタイム送受信

## 技術スタック概要

| レイヤー | 技術/ライブラリ |
|---------|---------------|
| フロントエンド | Thymeleaf, Bootstrap 5, jQuery, SockJS, STOMP |
| バックエンド | Kotlin 1.9.25, Spring Boot 3.4.4, Spring Security, Spring WebSocket |
| データストア | H2 Database(開発), PostgreSQL(本番), Spring Data JPA |
| ビルド/テスト | Gradle(Kotlin DSL), JUnit 5, MockK |

## モジュール構成

```
com.example.kotlin_thymeleaf_demo
├── application          # アプリケーション層
│   ├── config           # アプリケーション設定
│   └── security         # セキュリティ設定
├── domain               # ドメイン層
│   ├── user             # ユーザー関連
│   ├── post             # 投稿関連
│   ├── chat             # チャット関連
│   └── notification     # 通知関連
├── infrastructure       # インフラストラクチャ層
│   ├── repository       # リポジトリ実装
│   └── storage          # ファイルストレージ
└── presentation         # プレゼンテーション層
    ├── controller       # コントローラー
    ├── form             # フォーム
    └── advice           # コントローラーアドバイス
```

## 実装順序の概要

MVP実装は以下の順序で進めることを推奨します：

1. プロジェクトセットアップとデータベース設計
2. ユーザー管理機能の実装
3. 掲示板機能の実装
4. コメント機能の実装
5. WebSocket設定とチャット機能の実装
6. 機能統合とテスト

## エージェント利用のポイント

AIエージェントを活用した効率的な開発のために、以下の点を意識します：

1. **明確なタスク定義**: 各実装タスクを明確かつ小さな単位に分割
2. **コンテキスト共有**: 関連するファイルや依存関係を明示的に伝える
3. **段階的実装**: 最小単位で動作確認しながら段階的に機能を拡張
4. **テンプレートの活用**: 類似コンポーネントには一貫したパターンを使用
5. **ドメイン別アプローチ**: 機能横断ではなくドメイン単位での実装

## 次のステップ

このドキュメントセットを基に、以下の順序で実装を進めます：

1. プロジェクトの初期設定（依存関係の追加、アプリケーションプロパティの設定）
2. データベースのセットアップ（エンティティクラスの実装）
3. 各機能領域の実装（MVPに記載された優先順位に従って）

詳細な実装計画は [workflow/development-workflow.md](./workflow/development-workflow.md) を参照してください。
