メモ「現在の GitHub では、次の手順です。GitHub 公式もこの設定方法を案内しています。

公開したいリポジトリを開きます。たとえば https://github.com/taittinger/sample のようなリポジトリです。
上部の Settings をクリックします。Settings が見えなければ、上部の ... メニュー内にあります。
左側メニューを下へ進み、Code, planning, and automation の中の Pages をクリックします。

Build and deployment が表示されるので、Source を

Deploy from a branch

にします。

すると下に Branch が現れます。通常は次のようにします。

Branch
┌────────┐ ┌─────────┐
│ main ▼ │ │ /(root) │
└────────┘ └─────────┘
                 Save

つまり、

main
/(root)

を選び、Save を押します。

この設定の場合、リポジトリは次の構造になっている必要があります。

sample/
├── index.html   ← ここに必要
├── style.css
└── images/

index.html が、

sample/html/index.html

のように下のディレクトリに入っていると、main / (root) ではトップページとして見つけられません。GitHub Pages は、選択した公開フォルダの最上位に index.html があることを要求しています。ファイル名の大文字・小文字も区別されるので、Index.html ではなく index.html にしてください。」

公開するページがsjisの場合、utf-8に変換する。

Mac OSで、brew install nkf  をインストール

nkf -Sw --overwrite `find . -name "*.htm"` 

find . -type f -name '*.htm' |  xargs sed -i '' 's/charset=Shift_JIS/charset=utf-8/g'
