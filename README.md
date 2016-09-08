# We're still testing!!

# MemoryUtils

MemoryUtils consists of a set of methods to perform a variety of recurrent operations to deal with files in an Android device. It can be used to easily copy, save, delete files and objects forgetting about having to deal with those _Input/OutputStreams_ issues that crowd your code with `try/catch` blocks, making it unreadable.

# How To Use The Library

Add the library to your proyect from JCenter by including this line to your module's _build.gradle_ file:

`compile 'com.meleros-paw:memoryutil:0.0.2-beta'`

Then you can start calling _MemoryUtils_' methods passing a _Path_ to the files needed. Methods will then return a _Result_ containing a boolean to tell you whether the operation was successful or not, and the objects requested. Here's a quick example about how to save an object:

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

## What can I do with this library? 
Currently this library has methods to perform the following operations:
- Copying from an _InputStream_
- Importing a file, a database or a bitmap from assets
- Saving/loading a text file, an object, a `byte[]` or the _SharedPreferences_
- Duplicating, deleting a file
- Creating, emptying a folder
- Checking if a file exists, is a folder or is empty
- Getting the longest valid path from a path
- Getting a list of files in a directory
- Printing a folder's file tree
- Checking if external memory is available

See [Hot To Create Path Objects](https://github.com/Triodesabios/meleros-paw/wiki/1.-How-To-Create-Path-Objects) to learn how to create _Path_ objects; and [Functions](https://github.com/Triodesabios/meleros-paw/wiki/Functions) to learn how to use them.
