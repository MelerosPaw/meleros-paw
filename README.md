# MemoryUtil

MemoryUtil consists of a set of methods to perform a variety of recurrent operations to deal with files in an Android device. It can be used to easily copy, save, delete files and objects forgetting about having to deal with those _Input/OutputStreams_ issues that crowd your code with `try/catch` blocks and make it unreadable.

# How To Use The Library

Import the library to your proyect from JCenter by adding this line to your module's _build.gradle_ file:

```groovy
compile 'com.meleros-paw:memoryutil:1.1.3'
```

Then you can start calling _MemoryUtil_'s methods passing a _Path_ to the files needed. Methods will then return a _Result_ containing a boolean to tell you whether the operation was successful or not, and the objects requested. Here's a quick example about how to save an object:

```java
CustomObject myCustomObject;

Path path = new Path.Builder(context)
    .storageDirectory(Path.STORAGE_PUBLIC_EXTERNAL)
    .folder("mySavedObjects")
    .file("myCustomObject")
    .build();

Result<File> result = MemoryUtils.saveObject(path, myCustomObject);
if (result.isSuccessful){
   // Object has been saved to folder mySavedObjects in external public directory
}
```

_Path_ class methods uses the **system's own methods** to get the path to the storage directory, so it will not get deprecated through time, wich may happen if you hardcode your paths. In addition, the entry **parameters are validated** and exceptions will be thrown if you ever call a method using invalid parameters so you'll never let wrong calls to go unnoticed.

## What can I do with this library? 
Currently this library has methods to perform the following operations:
- Importing a file, a database or a bitmap from assets
- Saving/loading a text file, an object, a `byte[]` or the _SharedPreferences_
- Duplicating, deleting a file
- Creating, emptying a folder
- Checking if a file exists, is a folder or is empty
- Getting the longest valid path from a path
- Getting a list of files in a directory
- Printing a folder's file tree
- Checking if external memory is available
- Copying from an _InputStream_

See [How To Create Path Objects](https://github.com/Triodesabios/meleros-paw/wiki/1.-How-To-Create-Path-Objects) and then [Functionalities](https://github.com/Triodesabios/meleros-paw/wiki/Functionalities) to learn what you can do with them.
