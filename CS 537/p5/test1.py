#!/usr/bin/env python
import os
import sys
import util


def main():
    """ Test if there is a README or README.txt file.

    """
    
    if os.path.exists("./README") or os.path.exists("./README.txt"):
        util.good("You passed")
        sys.exit(0) # pass
    else:
        util.error("Missing README or README.txt")
        


if __name__ == "__main__":
    main()
