# 共通変数とデータベースの設計

## 検討事項：
- グレゴリオ暦やユリウス暦，和暦ごとの曜日の正解数に分けたほうが良いかも？その時は、曜日ごとのテーブルを用意したほうが良いか？
- 共通変数に、クイズモード選択時の設定の値を保持し、次にクイズモード選択画面ではその値をデフォルトにする。
- ゲームモード毎のベスト平均回答時間

## 共通変数名

| 共通変数名           | 型  | 使用用途                         |
| :------------------- | :-- | :------------------------------- |
| right_Sunday         | int | 答えが日曜日の問題を正解した回数 |
| right_Monday         | int | 答えが月曜日の問題を正解した回数 |
| right_Tuesday        | int | 答えが火曜日の問題を正解した回数 |
| right_Wednesday      | int | 答えが水曜日の問題を正解した回数 |
| right_Thursday       | int | 答えが木曜日の問題を正解した回数 |
| right_Friday         | int | 答えが金曜日の問題を正解した回数 |
| right_Saturday       | int | 答えが土曜日の問題を正解した回数 |
| total_number_of_quiz | int | 全部のクイズを通した正解数       |
| total_time           | int | ゲームを起動した秒数             |

## データベース名

| 属性名       | 型                                           | 使用用途                                           |
| :----------- | :------------------------------------------- | :------------------------------------------------- |
| id           |                                              | primary key                                        |
| created_on   | string (ex. 2021-9-13) or ISO8601 UTC format | データの作成日時。問題を出題順にソートする際に使う |
| quiz         | string (ex. 2021-9-13) or ISO8601 UTC format | ゲームで問題として出された、クイズ文中の日付       |
| answer       | string                                       | クイズの正解の曜日                                 |
| right_or_not | boolean                                      | クイズの答えが正解か不正解か |
