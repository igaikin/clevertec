# Краткая инструкция

Заказ осуществляется через файл resources/in/ClientOrder.txt в формате "[id]-[quantity] card-[id]"

Список продуктов и дисконтных карт можно посмотреть в resources/in/products.txt и cards.txt соответственно. получение
данных так же осуществляется из этих файлов.

Оставлена реализация для возможности чтения данных из HashMap<> CardDaoImpl и ProductDaoImpl
Добавлена реализация JDBC.
Скрипты для базы данных находятся в src/main/resources/SQL

Вывод данных реализован в файл resources/out/Check.txt и консоль одновременно

Log4J в перспективе...
