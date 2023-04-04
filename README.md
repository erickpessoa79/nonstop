# Nonstop

### Desktop executable file to avoid windows sleep. 
java 8 executable file type


Use:

``` shell
$ java -jar nonstop.jar
```

or click twice



### deploy

``` shell
git add .
git commit -m "<commit>"
git push github master
```

### integrate the remote changes
<p>
git push github master error: </br>
    ! [rejected]        master -> master (fetch first)</br>
        failed to push some refs to '<git repository>'</br>
        hint:</br>
            Updates were rejected because the remote contains work that you do</br>
            not have locally. This is usually caused by another repository pushing</br>
            to the same ref. You may want to first integrate the remote changes</br>
            (e.g., 'git pull <git repository> <branch>') before pushing again.</br>
</p>

``` shell
git pull https://github.com/erickpessoa79/nonstop.git master
```