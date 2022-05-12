#!/usr/bin/env bash

awk -i inplace -f update-from-gfx-mapping.awk *.json
