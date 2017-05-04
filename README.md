# HuffmanEncoderDecoder

There is a Makefile so that we can build the code using following command at terminal:
$ make
This command will produce two binary files: encoder and decoder.
As mentioned before, encoder will take one input file. We can run it using following command:
     $ java encoder <input_file_name>             
     Note: The input file name will be given as a command line argument. This file can have more than 100,000,000 lines and each line can contain a decimal value in the range of 0 to 999,999. 
Running encoder program will produce the output files with exact name "encoded.bin" and "code_table.txt". On the other hand, decoder will take two input files. We will run it using following command:
$ java decoder <encoded_file_name> <code_table_file_name>
Running decoder program will produce output file with exact name "decoded.txt".
