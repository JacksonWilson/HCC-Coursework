Since I am using packages in these labs, I cannot use the supplied SoldItem.java
because I cannot import a class from the default package.

To fix this, I created a conversion driver (located in the bin folder) to
convert the SoldItem objects stored in the .dat file to SoldItem objects that
can be deserialized from the SoldItem class in the labs.lab10 package.