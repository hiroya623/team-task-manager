Java・Spring Boot・MySQL のチーム向けタスク管理アプリ

STEP 1:開発環境の構築・プロジェクト作成
STEP 2:Java・Mavenの実行環境設定
STEP 3:MySQLデータベースの接続設定
STEP 4:ユーザー登録機能の実装
STEP 5:ログイン・ログアウト機能の実装
STEP 6:ユーザー認証・セキュリティ設定
STEP 7:タスク管理用のDB・クラスの作成
STEP 8:タスクの登録・編集・削除機能の実装
STEP 9:タスク名検索・ユーザー別表示・入力チェック
STEP 10:ステータスによる絞り込み機能
STEP 11:入力チェック・ステータスの検証強化
STEP 12:画面デザインの改善
STEP 13:テストの追加・動作確認
STEP 14:ER図・クラス図の作成
STEP 15:README作成・最終確認

STEP 1：開発環境の構築・プロジェクト作成
Java 21の開発環境を準備
VS CodeでSpring Bootプロジェクトを作成
Spring Web、Spring Data JPA、MySQL Driver、Thymeleaf、Validationなどの依存関係を設定
Maven Wrapperでビルドできることを確認

STEP 2：Java・Mavenの実行環境設定
Javaのインストール先を確認
JAVA_HOME などの環境変数を設定
VS CodeからJavaを認識できるように調整
BUILD SUCCESS が表示されることを確認

STEP 3：MySQLデータベースの接続設定
MySQLの接続状態を確認
team_task_manager データベースを作成
application.properties に接続情報を設定
環境変数 DB_PASSWORD からパスワードを読み込むように設定
Spring BootからMySQLに接続できることを確認

STEP 4：ユーザー登録機能の実装
User エンティティを作成
UserRepository を作成
UserService でユーザー登録処理を実装
UserController でユーザーAPIを作成
登録画面を作成
パスワードをハッシュ化して保存する仕組みを導入

STEP 5：ログイン・ログアウト機能の実装
ログイン画面を作成
ログイン後のダッシュボードを作成
ログアウト処理を実装
ログイン成功時・ログアウト時の遷移先を設定
未ログイン時に保護された画面へアクセスすると、ログイン画面へ遷移することを確認

STEP 6：ユーザー認証・セキュリティ設定
Spring Securityの設定
PasswordEncoder によるBCryptパスワードハッシュ化
CustomUserDetailsService によるユーザー認証
ログインにメールアドレスを使用する仕組みを設定
セッションやログアウト時の設定を調整

STEP 7：タスク管理用のDB・クラスの作成
Task エンティティを作成
TaskRepository を作成
TaskService を作成
TaskController を作成
タスクと登録ユーザーの関連付けを設定
作成日時・更新日時を自動設定する仕組みを実装
主なクラスの役割は次のとおりです。
クラス:役割
Task:タスクのデータを表す
TaskRepositoryDB:へのデータアクセス
TaskService:タスクに関する業務処理
TaskController:画面からのリクエストを処理
TaskRequest:新規登録時の入力データを受け取る

STEP 8：タスクの登録・編集・削除機能の実装
タスクの新規登録
タスクの一覧表示
タスクの編集
タスクの削除
削除時の確認ダイアログ
MySQLにデータが保存・更新・削除されることを確認

STEP 9：タスク名検索・ユーザー別表示・入力チェック
ログイン中のユーザーが登録したタスクだけを表示
タスク名のキーワード検索
検索解除による全タスク表示への復帰
タスク名の必須チェック
タスク名・説明の文字数制限
入力エラーのメッセージ表示

STEP 10：ステータスによる絞り込み機能
「未着手」「進行中」「完了」の選択欄を追加
ステータスに一致するタスクを検索
タスク名検索とステータス絞り込みの組み合わせに対応
検索解除で通常の一覧へ戻れることを確認

STEP 11：入力チェック・ステータスの検証強化
編集用DTO TaskUpdateRequest を作成
編集画面でDTOを使用するように変更
編集時にも @Valid による入力チェックを実装
編集画面に入力エラーメッセージを表示
ステータスを「未着手」「進行中」「完了」に制限する検証を追加
新規登録・編集の両方でステータスを検証
タスク名が空の場合に、Java側のエラーメッセージが表示されることを確認



以下chatを使用して作成中
https://chatgpt.com/share/6ac8755e-9be0-83e8-97e6-d0fe4e072681
