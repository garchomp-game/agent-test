# 実装ガイドライン

## 機能実装前のチェックリスト

実装を始める前に、以下の項目を確認してください：

- [ ] 設計ドキュメントの該当部分を読み直す (`docs/` 以下の関連ファイル)
- [ ] 必要な依存関係が `build.gradle.kts` に追加されているか確認
- [ ] 実装対象の機能に関連する既存のコードを確認
- [ ] 関連するデータモデル（エンティティ）のフィールドと関連性を確認 (特に `docs/database/database-design.md` と照合)
- [ ] 実装する機能のテストケースを検討
- [ ] APIエンドポイントが `docs/api/api-design.md` と整合しているか確認

## 機能実装中のチェックリスト

実装中は以下の点に注意してください：

- [ ] 命名規則に一貫性があるか確認（CamelCase vs snake_case）
- [ ] Nullセーフティを確保（Kotlinの `?` や `!!` の適切な使用）
- [ ] 実装コードが設計ドキュメントと一致しているか確認 (特にデータ構造、API仕様)
- [ ] 既存のコードスタイルに合わせているか確認
- [ ] エラー処理と例外ハンドリングが適切か確認
- [ ] **ハルシネーションチェック**: 存在しないクラスやメソッドを参照していないか？

## 機能実装後のチェックリスト

実装完了後は以下の確認を行ってください：

- [ ] コンパイルエラーがないことを確認（`./gradlew build`）
- [ ] テストが成功することを確認（`./gradlew test`）
- [ ] 動作確認（該当機能の手動テスト）
- [ ] コードの重複がないか確認
- [ ] セキュリティの脆弱性がないか確認
- [ ] **設計整合性チェック**: 実装が `docs/` 以下の設計ドキュメントと一致しているか最終確認

## 機能別の実装参照情報

### ユーザー管理機能

#### 主要クラス
- `User` エンティティ
- `UserRepository` インターフェース
- `UserService` クラス
- `SecurityConfig` クラス
- `UserController` クラス

#### 依存関係
```kotlin
// build.gradle.kts
implementation("org.springframework.boot:spring-boot-starter-security")
implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
```

#### 主要設定
```properties
# application.properties
spring.security.user.name=admin
spring.security.user.password=password
```

### 掲示板機能

#### 主要クラス
- `Post` エンティティ
- `Category` エンティティ
- `PostRepository` インターフェース
- `CategoryRepository` インターフェース
- `PostService` クラス
- `PostController` クラス

#### 依存関係
```kotlin
// build.gradle.kts
implementation("org.springframework.boot:spring-boot-starter-validation")
```

### コメント機能

#### 主要クラス
- `Comment` エンティティ
- `CommentRepository` インターフェース
- `CommentService` クラス
- `CommentController` クラス（または PostController 内のメソッド）

### チャット機能

#### 主要クラス
- `ChatRoom` エンティティ
- `ChatMessage` エンティティ
- `ChatRoomRepository` インターフェース
- `ChatMessageRepository` インターフェース
- `ChatService` クラス
- `WebSocketConfig` クラス
- `ChatController` クラス
- `WebSocketController` クラス

#### 依存関係
```kotlin
// build.gradle.kts
implementation("org.springframework.boot:spring-boot-starter-websocket")
implementation("org.webjars:stomp-websocket:2.3.4")
implementation("org.webjars:sockjs-client:1.5.1")
```

## データベースマイグレーション

開発中にデータベーススキーマを変更する場合は、以下の手順に従ってください：

1. エンティティクラスを更新
2. 開発環境では `spring.jpa.hibernate.ddl-auto=update` を使用可能
3. 重要なデータがある場合はバックアップを取得
4. 本番環境へのデプロイ前には適切なマイグレーションスクリプトを用意

## よくあるエラーと解決策

### JPA関連のエラー

#### LazyInitializationException
- 原因: セッション外でLazy Loadingを実行
- 解決策: `@Transactional` アノテーションの使用または Eager Fetchingに変更

#### Could not determine type for: xxx
- 原因: エンティティ間の関連付けが不正確
- 解決策: `@OneToMany`, `@ManyToOne` などの関連アノテーションを確認

### Spring Security関連のエラー

#### Access Denied
- 原因: 権限不足またはセキュリティ設定の誤り
- 解決策: `SecurityConfig` の設定確認、適切な権限を付与

#### Invalid CSRF Token
- 原因: CSRFトークンが欠落または無効
- 解決策: フォームにCSRFトークンを追加、または特定のエンドポイントでCSRF保護を無効化

### WebSocket関連のエラー

#### Connection Refused
- 原因: WebSocketサーバーが実行されていないまたは設定が誤っている
- 解決策: `WebSocketConfig` の設定確認、SockJSクライアントの設定確認

#### Message Not Delivered
- 原因: 送信先のエンドポイントが間違っている
- 解決策: メッセージの送信先（destination）を確認、認証設定を確認

### 設計ドキュメントとの不整合 (新規追加)

- **原因**: 実装が `docs/` 以下の設計ドキュメント（ER図、API定義など）と異なっている。
- **解決策**: 設計ドキュメントを正とし、実装を修正する。もし設計自体に問題がある場合は、ユーザーに確認し、設計ドキュメントを更新してから実装を修正する。
