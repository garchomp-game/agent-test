# 効果的なプロンプト作成ガイド

## プロンプト作成の基本原則

AIエージェントと効率的に協働するためのプロンプト作成のポイントをまとめました。

### 明確で具体的な指示を与える

**良い例**:
```
User: ユーザー登録機能を実装してください。具体的には以下の要素が必要です：
- Userエンティティ（id, username, email, password, createdAt, updatedAt フィールド）
- UserRepositoryインターフェース
- UserServiceの基本実装
- 登録フォームとバリデーション
```

**避けるべき例**:
```
User: ユーザー機能を作ってください
```

### コンテキストを提供する

**良い例**:
```
User: このプロジェクトでは、現在までにユーザーモデルとリポジトリを実装しました。
次のステップとして、Spring Securityを使った認証機能を追加したいと思います。
src/main/kotlin/com/example/kotlin_thymeleaf_demo/domain/user/User.kt ファイルに
ユーザーエンティティがあります。
```

**避けるべき例**:
```
User: 認証機能を追加してください
```

### 参照すべき既存のコードを具体的に示す

**良い例**:
```
User: Post.ktのエンティティを参考にして、Commentエンティティを作成してください。
以下はPost.ktの内容です：

```kotlin
@Entity
@Table(name = "posts")
class Post(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    var title: String,
    
    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: Category? = null,
    
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var isDeleted: Boolean = false
)
```
```

### 回答の形式を指定する

**良い例**:
```
User: WebSocketConfigクラスを作成してください。以下の要件を満たす必要があります：
1. STOMPプロトコルの有効化
2. エンドポイントを /ws に設定
3. /app と /topic プレフィックスの設定

コードブロックで回答し、各設定の目的を簡単なコメントで説明してください。
```

### トラブルシューティング情報を詳細に提供する

**良い例**:
```
User: 以下のエラーが発生しています。解決方法を教えてください。

エラーメッセージ:
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'postController': Unsatisfied dependency expressed through constructor parameter 0

関連するコード:
PostController.kt:
```kotlin
@Controller
@RequestMapping("/posts")
class PostController(
    private val postService: PostService,
    private val categoryService: CategoryService
) {
    // ...
}
```

PostService.kt:
```kotlin
@Service
class PostService(private val postRepository: PostRepository) {
    // ...
}
```
```

## プロンプト例集

### 新機能の追加依頼

```
以下の要件に基づいて、投稿へのリアクション機能を実装してください：

1. Reactionエンティティが必要（id, userId, targetId, targetType, reactionType, createdAt）
2. targetTypeは "post" または "comment" を指定
3. reactionTypeは "like", "great", "helpful" のいずれか
4. 同じユーザーが同じ対象に複数回リアクションしないよう制約が必要
5. リアクション追加/削除のRESTエンドポイントの実装

既存のエンティティとの関連について：
- User.kt (id, username, email, ...)
- Post.kt (id, title, content, userId, ...)
- Comment.kt (id, content, postId, userId, ...)
```

### バグ修正の依頼

```
チャットメッセージの送信機能に問題があります。メッセージはデータベースに保存されますが、WebSocketを通じて他のユーザーに配信されません。

関連ファイル：
1. ChatController.kt - メッセージ受信と保存を担当
2. WebSocketConfig.kt - WebSocket設定を担当
3. chat.js - フロントエンドのWebSocket処理

特に以下のコードを確認してください：
```javascript
// chat.js
stompClient.send("/app/chat/" + roomId + "/send", {}, 
    JSON.stringify({content: message, type: "TEXT"}));
```

```kotlin
// ChatController.kt
@MessageMapping("/chat/{roomId}/send")
@SendTo("/topic/chat/{roomId}")
fun sendMessage(@DestinationVariable roomId: Long, message: ChatMessageDto): ChatMessageResponse {
    // メッセージ処理コード
}
```
```

### コード改善の依頼

```
現在のPostServiceクラスに、性能上の問題があります。大量の投稿を取得する際にN+1問題が発生しているようです。

以下のコードを最適化してください：

```kotlin
@Service
class PostService(private val postRepository: PostRepository) {
    
    fun getAllPosts(): List<PostDto> {
        return postRepository.findAll().map { post ->
            PostDto(
                id = post.id!!,
                title = post.title,
                content = post.content,
                authorName = post.user.username,  // ここでN+1問題が発生
                categoryName = post.category?.name,  // ここでも発生する可能性
                createdAt = post.createdAt
            )
        }
    }
}
```

JPA/Hibernateのフェッチ戦略を適切に設定して、性能を改善してください。
```

## プロンプト作成時の注意点

1. **一度に依頼する内容を限定する**: 複数の無関係な機能を一度に依頼するより、関連する機能ごとに分けて依頼する

2. **既存コードの参照方法を明確にする**: ファイルパスと内容を具体的に示す

3. **実装の優先順位を伝える**: 複雑な機能の場合、「まずはXを実装し、その後Yを追加」のように段階的に指示

4. **制約条件を明確にする**: パフォーマンス要件やセキュリティ要件など、特別な制約がある場合は明示する

5. **フィードバックを具体的に伝える**: 「動作しません」ではなく「XのAPIを呼び出すと404エラーが返ります」のように具体的に

---

これらのガイドラインを参考に、AIエージェントとの効果的なコミュニケーションを実現し、開発効率を高めましょう。
