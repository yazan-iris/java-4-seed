# java-4-seed
A Java API for reading dataless SEED.

```java
BlocketteDirector director = new BlocketteDirector(new BlocketteBuilder());
BlocketteItrator iterator = director.process(inputStream);
  
Volume volume = new Volume();
while (iterator.hasNext()) {
    Blockette blockette = iterator.next();
    volume.add(blockette);
}
```
