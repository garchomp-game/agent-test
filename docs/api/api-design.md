# API設計

## RESTful API エンドポイント

MVPで実装するRESTful APIエンドポイントの一覧です。

### ユーザー管理API

| メソッド | エンドポイント | 説明 | リクエスト | レスポンス |
|---------|-------------|------|-----------|-----------|
| POST | /api/register | ユーザー登録 | ユーザー情報（名前、メール、パスワード） | ユーザー情報（ID含む） |
| POST | /api/login | ログイン | メール、パスワード | 認証トークン、ユーザー情報 |
| GET | /api/users/current | 現在のユーザー情報取得 | トークン（ヘッダー） | ユーザー情報 |
| PUT | /api/users/current | ユーザー情報更新 | トークン（ヘッダー）、更新情報 | 更新後のユーザー情報 |

### 投稿API

| メソッド | エンドポイント | 説明 | リクエスト | レスポンス |
|---------|-------------|------|-----------|-----------|
| GET | /api/posts | 投稿一覧取得 | ページパラメータ（オプション） | 投稿リスト、ページ情報 |
| GET | /api/posts/{id} | 投稿詳細取得 | 投稿ID | 投稿詳細、コメント一覧 |
| POST | /api/posts | 投稿作成 | トークン（ヘッダー）、投稿内容 | 作成された投稿情報 |
| PUT | /api/posts/{id} | 投稿更新 | トークン（ヘッダー）、投稿ID、更新内容 | 更新された投稿情報 |
| DELETE | /api/posts/{id} | 投稿削除 | トークン（ヘッダー）、投稿ID | 成功ステータス |

### コメントAPI

| メソッド | エンドポイント | 説明 | リクエスト | レスポンス |
|---------|-------------|------|-----------|-----------|
| GET | /api/posts/{postId}/comments | コメント一覧取得 | 投稿ID | コメントリスト |
| POST | /api/posts/{postId}/comments | コメント作成 | トークン（ヘッダー）、投稿ID、コメント内容 | 作成されたコメント情報 |
| PUT | /api/comments/{id} | コメント更新 | トークン（ヘッダー）、コメントID、更新内容 | 更新されたコメント情報 |
| DELETE | /api/comments/{id} | コメント削除 | トークン（ヘッダー）、コメントID | 成功ステータス |

### チャットAPI

| メソッド | エンドポイント | 説明 | リクエスト | レスポンス |
|---------|-------------|------|-----------|-----------|
| GET | /api/chat/rooms | チャットルーム一覧取得 | トークン（ヘッダー） | チャットルームリスト |
| GET | /api/chat/rooms/{id} | チャットルーム詳細取得 | トークン（ヘッダー）、ルームID | ルーム詳細、メッセージ履歴 |
| POST | /api/chat/rooms | チャットルーム作成 | トークン（ヘッダー）、ルーム情報 | 作成されたルーム情報 |
| POST | /api/chat/rooms/{id}/join | チャットルーム参加 | トークン（ヘッダー）、ルームID | 成功ステータス |
| POST | /api/chat/rooms/{id}/leave | チャットルーム退出 | トークン（ヘッダー）、ルームID | 成功ステータス |

## WebSocket API

チャット機能はWebSocketを使用してリアルタイム通信を実現します。

### エンドポイント

- 接続エンドポイント: `/ws`
- サブプロトコル: STOMP (Simple Text Oriented Messaging Protocol)

### メッセージトピック

| タイプ | トピック | 説明 |
|-------|--------|------|
| 購読 | /topic/chat/{roomId} | 特定のチャットルームのメッセージを受信 |
| 送信 | /app/chat/{roomId}/send | 特定のチャットルームにメッセージを送信 |
| 購読 | /user/queue/notifications | ユーザー固有の通知を受信 |
| 購読 | /topic/chat/{roomId}/typing | 入力中ステータスの通知 |
| 送信 | /app/chat/{roomId}/typing | 入力中ステータスの送信 |

### メッセージ構造

#### チャットメッセージ送信
```json
{
  "content": "こんにちは！",
  "type": "TEXT",
  "attachmentUrl": null
}
```

#### チャットメッセージ受信
```json
{
  "id": 1,
  "content": "こんにちは！",
  "type": "TEXT",
  "attachmentUrl": null,
  "sender": {
    "id": 123,
    "username": "user1",
    "profileImageUrl": "/images/avatars/user1.jpg"
  },
  "timestamp": "2025-04-15T10:30:45"
}
```

#### 入力中ステータス
```json
{
  "userId": 123,
  "username": "user1",
  "typing": true
}
```

#### 通知メッセージ
```json
{
  "id": 1,
  "type": "NEW_MESSAGE",
  "content": "新しいメッセージがあります",
  "referenceId": 456,
  "referenceType": "CHAT_MESSAGE",
  "timestamp": "2025-04-15T10:30:45"
}
```

## API レスポンス形式

すべてのJSON APIレスポンスは以下の共通構造に従います：

### 成功レスポンス
```json
{
  "status": "success",
  "data": {
    // リソース固有のデータ
  },
  "message": null
}
```

### エラーレスポンス
```json
{
  "status": "error",
  "data": null,
  "message": "エラーメッセージ",
  "errors": [
    {
      "field": "フィールド名",
      "message": "検証エラーメッセージ"
    }
  ]
}
```

## ページネーション

リスト取得APIはページネーションをサポートします：

### リクエストパラメータ
- `page`: ページ番号（0ベース、デフォルト0）
- `size`: 1ページあたりのアイテム数（デフォルト20、最大50）
- `sort`: ソートフィールドと方向（例: `createdAt,desc`）

### レスポンス形式
```json
{
  "status": "success",
  "data": {
    "content": [
      // リソースのリスト
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 20,
      "sort": {
        "sorted": true,
        "unsorted": false
      }
    },
    "totalPages": 5,
    "totalElements": 100,
    "last": false,
    "first": true,
    "number": 0,
    "size": 20,
    "numberOfElements": 20,
    "empty": false
  },
  "message": null
}
```

## Thymeleafコントローラー

Thymeleafテンプレートにデータを提供するコントローラーの主要なエンドポイントです。

| エンドポイント | 説明 | テンプレート | モデル属性 |
|-------------|------|------------|-----------|
| / | ホームページ | home | posts, categories, popularTags |
| /login | ログインページ | login | - |
| /register | 登録ページ | register | registrationForm |
| /posts | 投稿一覧 | posts/index | posts, categories, tags |
| /posts/{id} | 投稿詳細 | posts/show | post, comments, commentForm |
| /posts/new | 投稿作成 | posts/form | postForm, categories |
| /posts/{id}/edit | 投稿編集 | posts/form | postForm, categories |
| /profile | ユーザープロフィール | users/profile | user, userPosts |
| /profile/edit | プロフィール編集 | users/edit | userForm |
| /chat | チャットルーム一覧 | chat/index | chatRooms |
| /chat/{id} | チャットルーム | chat/room | chatRoom, messages |

## 認証・認可

認証と認可には以下の方式を使用します：

1. **Web認証**: Spring Securityによるフォーム認証
2. **API認証**: JWTトークンベースの認証
3. **WebSocket認証**: STOMP認証ヘッダー + セッションベース認証

### ロールと権限

| ロール | 権限 |
|-------|------|
| ROLE_USER | 投稿閲覧、投稿作成、コメント投稿、チャット参加 |
| ROLE_ADMIN | すべての権限 + 投稿/コメント管理、ユーザー管理 |

### セキュリティ設定

```kotlin
@Configuration
@EnableWebSecurity
class SecurityConfig(private val userService: UserService) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                    .requestMatchers("/api/register", "/api/login").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .formLogin { form ->
                form
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
            }
            .csrf { csrf ->
                csrf.ignoringRequestMatchers("/api/**", "/ws/**")
            }
            
        return http.build()
    }
    
    // パスワードエンコーダー等の設定...
}
```
