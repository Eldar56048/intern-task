@settings {
  font-size: 100;
}

**1.Все тесты работают кроме SocketTest (не смог сделать репозиторий mock , чтобы он возвращал room  а не лезь в бд. Если убрать mock Bean RoomRepository и где написано when то он полезет в бд если есть комната под ид 100 то он сработает можете сами проверить поставив нужный ид).
**2. Чтобы запустить поменяйте в application.properties mysql  username password and datasource url. ****
