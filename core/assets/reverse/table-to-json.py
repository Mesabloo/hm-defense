#!/usr/bin/env python3

import sys
import os
import json

# Record all files to modify
files = sys.argv[1:]

# So the format of the .log files is a bit strange:
# - Every entry is annotated with a column index.
#   This means that we have to group every item with the same column number inside the same JSON object.
# - The top-level object is issued from a dump of an Obj-C map.
#   This means that we have to reconstruct an array of objects.
# - We have to parse everything by hand, most likely.


# Step 1: parse and keep all information in a specific structure (an array of dictionnaries).
def insert_info_into_array(index, key, value):
    global data_array

    data_array += [None] * (index - len(data_array) + 1)

    if data_array[index] is None:
        d = dict()
    else:
        d = data_array[index]
    d[key] = value

    data_array[index] = d


# This will hold all our beloved information.
data_array = []


# Now the parsing
def parse_file(content):
    lbrace, *content = content

    if lbrace != '{':
        return (False, lbrace + ''.join(content))

    # Parse every entry in the file until there are no more of there is a parse error.
    will_continue, content = parse_item(content)
    while will_continue:
        will_continue, content = parse_item(content)

    rbrace = content[0]

    if rbrace != '}':
        return (False, content)

    # Ideally, `content == ''`. However, if we found a `}` after all entries, then there should not be
    # anything left after.
    return (True, content[1:])


def parse_item(content):
    # Remove some unneeded space before the entry.
    while content[0].isspace():
        content = content[1:]

    lquote, *content = content
    if lquote != '"':
        return (False, lquote + ''.join(content))

    not_failed, index, content = parse_integer(content)
    if not not_failed:
        return (False, content)

    at, *content = content
    if at != '@':
        return (False, at + ''.join(content))

    key = ''
    while content[0] != '"':
        key += content[0]
        content = content[1:]

    rquote, *content = content
    if rquote != '"':
        return (False, rquote + ''.join(content))

    # Ignore middle sequence ` = `.
    content = content[3:]

    not_failed = False
    parsers = iter([parse_string, parse_identifier, parse_integer])
    value = None
    try:
        while not not_failed:
            fn = next(parsers)

            not_failed, value, content = fn(content)
    except StopIteration:
        return (False, content)

    # Ignore ending `;`.
    content = content[1:]

    # Remove unneeded space characters after the entry.
    while content[0].isspace():
        content = content[1:]

    # Insert gotten information into the global data array.
    insert_info_into_array(index, key, value)

    return (True, content)

def parse_string(content):
    lquote, *content = content
    if lquote != '"':
        return (False, None, lquote + ''.join(content))

    value = ''
    while content[0] != '"':
        value += content[0]
        content = content[1:]

    rquote, *content = content
    if rquote != '"':
        return (False, None, rquote + ''.join(content))

    return (True, value, content)

def parse_integer(content):
    value = 0
    input_changed = False

    while content[0].isdigit():
        value = value * 10 + int(content[0])
        content = content[1:]
        input_changed = True

    return (input_changed, value, content)

def parse_identifier(content):
    value = ''
    
    if content[0].isalpha():
        value += content[0]
        content = content[1:]
    while content[0].isalnum():
        value += content[0]
        content = content[1:]

    return (len(value) > 0, value, content)


for filename in files:
    json_name, _ = os.path.splitext(filename)
    json_name += '.json'
    with open(filename, 'r') as handle:
        content = handle.read()

    not_failed, rem = parse_file(content[:])

    if not_failed:
        json_data = json.dumps(data_array)

        with open(json_name, 'w') as handle:
            handle.write(json_data)

        print("Processed file " + filename + " to JSON " + json_name)
    else:
        print("Parsing has failed while processing file " + filename + ":")
        print("Remaining input: (100 first characters)")
        print(''.join(rem[:100]))

        sys.exit(1)

    data_array = []
