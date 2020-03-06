# AI_Bartender
職訓專題 - AI Bartender

# 使用JNDI方式連線資料庫

# 設計規範
1. 設計SQL語句的class檔名結尾請寫Dao(e.g.PODao)
2. Servlet程式的檔案檔名結尾請寫Servlet(e.g.POServlet)
3. Entity class 檔名要與 DB Table名相同(e.g.PO)
4. 盡量將JavaScript、CSS、HTML、Java分開寫在各自的檔案內(並放入相應的目錄下)
	前台的jsp頁面請放在jsp/front目錄下，後台的jsp頁面請放在jsp/back目錄下
5. 在java檔中接收jsp檔的欄位資料請使用以下function接值
	String 類使用 CommonUtil.getParseString()
	int 類使用 CommonUtil.getParseInt()
	這樣可避免發生接到空值而造成錯誤的情況
6. 盡量自己改自己的檔案，若需要更動到他人檔案請告知對方修改，以免造成程式衝突(版控要merge很麻煩)

# 禁止上傳的檔案
1. .classpath
2. config.properties
3. .gitignore
