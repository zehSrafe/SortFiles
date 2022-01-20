from pathlib import Path
from time import ctime

# Makes sense:

new_line = '\n'
file_name = 'original.txt'
mr_robot = Path('/home/elliot/PycharmProjects/python_basics/exceptions/txt-files/second_file.txt')
new_file_name = file_name.capitalize()  # A good programmer is a lazy programmer :D

# In this class we will look at the handy methods to handle files with Python:

path = Path('/home/elliot/PycharmProjects/python_basics/python-standard-lib/txt-files')
txt_file = path / file_name  # Check WorkingWithPaths.py if this makes no sense ;)

py_files = [p for p in Path('/home/elliot/PycharmProjects/python_basics').rglob('*.py')]


def show_stats(files):
    for f in files:
        print(f"The file {f.name} exists {f.exists()}")  # This method checks if the file exists
        print(f.stat(), new_line)  # info about returned values see: https://docs.python.org/3/library/os.html#os.stat


# We can use this handy function, to get info about our files and see if it exists:

show_stats(py_files)

# If we want to see the creation time we could do this as follows:

print(f"Time of creation: {ctime(txt_file.stat().st_ctime)}{new_line}")  # ctime function converts seconds from epoch

# We can rename a file:

txt_file.rename(new_file_name)
txt_file = path / new_file_name  # Of course we need to update our path then

# We can delete a file uncomment to see deletion works:

# txt_file.unlink()  # Dont forget to make a new Original.txt file, when you want to run this code more than once!!!

# Lets create a new path obj pointing to a text file:

intro_file = path / 'Intro.txt'

# We can also read the file as a bytestream:

byte_stream = intro_file.read_bytes()  # returns content as a byte object

# Let's see what we get as a result:
print(f"Byte object: {byte_stream}{new_line}")

# We can read the content of a file like this:
print(f"Content of the file: {new_line}{intro_file.read_text()}{new_line}")

# All this is handled inside the read_text method for us:

# TODO has a weird little bug, when coming across an empty line, he printouts 3 empty lines

# with open(intro_file, 'r') as file:
#     line = file.readline()
#
#     while line:
#         print(line)
#         line = file.readline()

# We can also write to a file as such:

intro_file.write_text('write_text method was here, btw pressing control + z will bring the text back ;)')
