# 掲示板システム解答例

研修の課題の解答例プロジェクトです。  

## 環境
 * SpringBoot
 * Thymeleaf
 * Postgresql

## 動作確認手順
 * sqlフォルダの1createtable.txtを実施
 * SpringBootアプリケーションとして起動
 * 以下のURLにアクセス
 * http://localhost:8080/separatedbbs (テーブル非結合)
 * http://localhost:8080/joinedbbs (テーブル結合)

- - -

## 本プロジェクトはパフォーマンスチューニングの講義でも使用します
### パフォーマンスチューニング実施手順
 * sqlフォルダの1createtable.txtと2insert.txtを実行
 * joinedとseparetedの速度の違いを確認する
 * INDEXの説明をする
 * INDEXを貼っていない状態でseparatedをもう一度実行し速度を把握する
 * 3createindex.txtを実行し、INDEXを貼る
 * 再度separatedを実行し速度の違いを確認する
