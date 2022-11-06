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

Создать функцию
```sql
CREATE OR REPLACE FUNCTION removeNonDigit(source varchar(3000)) RETURNS INTEGER LANGUAGE JAVA DETERMINISTIC NO SQL NOT FENCED EXTERNAL NAME 'myjar:RemoveNonDigit!removeNonDigit' PARAMETER STYLE JAVA NO EXTERNAL ACTION
```

Протестировать
```sql
SELECT removeNonDigit(wonum) FROM WORKORDER w FETCH FIRST 10 ROWS ONLY;
```

Если получаем SQLCODE

## Документация

* https://www.ibm.com/docs/en/db2-for-zos/11?topic=objects-creation-user-defined-functions
* https://www.ibm.com/docs/en/i/7.4?topic=files-sqljinstall-jar
* https://stackovergo.com/ru/q/2483926/error--4304-when-calling-a-java-stored-procedure-in-db2
* https://db2guideonline.blogspot.com/2014/11/how-to-write-own-java-user-defined.html
* https://db2guideonline.blogspot.com/2014/11/how-to-write-own-java-user-defined.html


Some of the Trouble shooting :


         You may get the error : SQL4301N Java or .NET interpreter startup or communication failed, reason code "0". SQLSTATE=58004
          Please check JDK_PATH by the command

```shell
In windows :             db2 get dbm cfg | find "JDK".
In Linux :               db2 get dbm cfg | grep -i JDK
```

The JDK_PATH is one level up to bin directory. So Update the path using the following command.
```db2
db2 update dbm cfg using JDK_PATH = E:\DB2\IBM\SQLLIB\java\jdk
```
           If the above error with reaon code "4" means you can check JAVA_HEAP_SZ. It should be enough to handle the User Defined function / Stored Procedure. 512 may be enough to handle. You can change the value using the following command.
```db2
db2 update dbm cfg using JAVA_HEAP_SZ 4096
```
Java stored procedure or user-defined function could not load Java class reason code "1". SQLSTATE=42724

Class Name mentioned in the Create Function may be mis-spelled. This error may be generated using java.lang.UnsupportedClassError. You have to ensure you are compiling and running the java class file using the same jdk version. Error may be seen E:\DB2\IBM\SQLLIB\DB2\db2dialog.log.