●アプリケーション名
【在庫管理アプリ】

●アプリケーション概要
生産現場において在庫の管理をウェブ上で確認できるアプリケーション

●動作確認（youtube)

  ※今後dockerを使用してawsで稼働予定

●作成環境
・STS  (Exlipse)
      Spring boot  2.5.9  
      タイプ　 Gradle    
      使用言語　 Java　 １１  
      
      スターター
      JDBC API   Lombok    MySQL Driver    Spring Boot Devtools    Spring Security
      Spring Web     Thymeleaf     Validation
      
・DB    (Mysql)  
      ER図（今後追加機能予定DB分も追記）  
      https://lucid.app/lucidchart/4089a3f5-574d-4b61-9ca6-ac88f6dbd90e/edit?invitationId=inv_4499cdbc-bf9e-4e0e-811f-45607e817264

●使用機能  
Spring Security を使用したログイン機能  
DBとの連携による、CRUD機能で、商品在庫と商品登録が可能  
Bootstrapでできる限りレスポンシブルデザインを実装  
一部Javascriptで入力の確認などを行っている  
  
  ※追加予定  
    出荷一覧を作成して、出荷内容を確認する及び、出荷伝票等をwordに出力する  
   　　各機能をテスト開発を行う  
     
●作成者  
　　furutani  

