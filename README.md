# DirectionCompression

Both the Rest endpoints(/compress and /decompress) optionally accept parralelCount.
To simply invoke added test.html in the repo https://github.com/intrealm/DirectionCompression/blob/master/test.html.

#####Compression
  -Source hierarchical structure to flat target folder with compression files
  -An upper limit on the size of the compressed files(splitting)

#####Decompression
  -Source flat folder to target folder with original hierarchical structure and original file names.
  -Additionally, decompression can run also run in parallel for a source directory
     Capable of decompressing in parallel when the optional body param parallelcount is passed above 1.
