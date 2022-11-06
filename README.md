# Внешние функции и процедуры для Db2

В Db2 (Oracle) можно подключать внешние функции, 

для этого нужно написать класс, например поиск чисел в строке

```java
import java.io.*;
public class RemoveNonDigit
{
public static int removeNonDigit(String line)  throws IOException 
{
    String clean = line.replaceAll("\\D+","");
    if(clean == "") {
        return 0;
    }
    return Integer.parseInt(clean);
    
}
}
```

После этого необходимо на сервере скомпилировать функцию 

```bash
db2 get dbm cfg | grep -i JDK
 Java Development Kit installation path       (JDK_PATH) = /home/ctginst1/sqllib/java/jdk64
```

Скомпилировать

```bash
 /home/ctginst1/sqllib/java/jdk64/bin/javac RemoveNonDigit.java 
 /home/ctginst1/sqllib/java/jdk64/bin/jar cf removenondigit.jar RemoveNonDigit.class 
```

В консоли db2

```db2
connect to dbname
CALL SQLJ.INSTALL_JAR('file:/home/ctginst1/removenondigit.jar','testmy');
```

Если была замена классов
```db2
CALL SQLJ.REFRESH_CLASSES()
```

## Документация

https://www.ibm.com/docs/en/db2-for-zos/11?topic=objects-creation-user-defined-functions