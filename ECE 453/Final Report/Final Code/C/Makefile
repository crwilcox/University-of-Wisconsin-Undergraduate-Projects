PROGRAM_NAME = pong
SRC_FILES = main.c gameMode.c tableLibrary.c demoMode.c

#CROSS_COMPILE ?= arm-none-linux-gnueabi-
CROSS_COMPILE ?=

LIBS = -lpthread -ggdb

OBJ_FILES = $(SRC_FILES:%.c=%.o)
DEP_FILES = $(SRC_FILES:%.c=%.d)

$(PROGRAM_NAME): $(OBJ_FILES)
	$(CROSS_COMPILE)gcc -o $@ $? $(LIBS) -D DEBUG
	rm -f $(OBJ_FILES) $(DEP_FILES)

%.o: %.c
	$(CROSS_COMPILE)gcc -M -c -o $*.d $< -ggdb -D DEBUG
	$(CROSS_COMPILE)gcc -c -o $@ $< -ggdb -D DEBUG

.PHONY: clean
clean:
	rm -f $(PROGRAM_NAME) $(OBJ_FILES) $(DEP_FILES)

-include $(DEP_FILES)


