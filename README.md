# java-4-seed
Read and write Seed files as described by the Standard for the Exchange of Earthquake Data,
SEED Format Version 2.4, August, 2012.

```java
File source=new File("");
File target=new File("");
try (FileInputStream fileInputStream = new FileInputStream(source);
    OutputStream fileOutputStream = new FileOutputStream(target)) {
			SeedVolume volume = SeedIOUtils.toSeedVolume(fileInputStream);
			
			for(Blockette b:volume.blockettes()) {
				//do Something
			}
		} catch (SeedException e) {

}
```
```java
File source = new File("");
File target = new File("");
try (FileInputStream fileInputStream = new FileInputStream(source);
  OutputStream fileOutputStream = new FileOutputStream(target)) {
    SeedBlocketteIterator it = SeedIOUtils.toBlocketteIterator(fileInputStream);
    while (it.hasNext()) {
      Blockette b = it.next();
				// do Something
      }
    }
}
```

```java
File source=new File("");
File target=new File("");
try (FileInputStream fileInputStream = new FileInputStream(source);
  OutputStream fileOutputStream = new FileOutputStream(target)) {
    List<Timeseries> list = SeedIOUtils.toTimeseries(fileInputStream, false);
			
		for(Timeseries ts:list) {
				//do Something
		}
} catch (SeedException e) {
}
```
