This folder contains some Markdown files describing the content of the JSON stat files.
Every Markdown file `X.md` describes the JSON file `X.json`.
The table present in each has default values alongside every key which must be present in the file.
As an example, the following table is for a JSON file of this form:

| Key | Value |
| --- | ----- |
| "A" | "B"   |
| "C" | "D"   |

```json
[
  {
    "Key": "A",
    "Value": "B"
  },
  {
    "Key": "C",
    "Value": "D"
  }
]
```

Note that literal strings are enclosed in double quotes `"`, while integer constants are just raw numbers.
Absence of value in a cell means that you may put whatever you want without breaking the game (or at least minorly⁽¹⁾).

The Markdown tables, once filled, can then be transformed to JSON using [this website](https://tableconvert.com/markdown-to-json) (check `Parsing JSON` if you do so, in order not to include additional unwanted `"`, and use the format `Array of Object`).

> ⁽¹⁾: Required values are always put in the tables and must not be modified (unless stated otherwise), in which case the game may not work at all.
> This does not apply *only* to the field `Value` for translation files (the files named `Description_??.json`).
