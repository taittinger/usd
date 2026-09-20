公開するページがsjisの場合、utf-8に変換する。

Mac OSで、brew install nkf  をインストール

nkf -Sw --overwrite `find . -name "*.htm"` 

find . -type f -name '*.htm' |  xargs sed -i '' 's/charset=Shift_JIS/charset=utf-8/g'
