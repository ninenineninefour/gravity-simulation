------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE:
PURPOSE OF PROJECT:
VERSION or DATE:
HOW TO START THIS PROJECT:
AUTHORS:
USER INSTRUCTIONS:

        if(e.getSource() == pushPlace1)
        {
            if(frame.showPlace1)
            {
                pushPlace1.setText("Show Place2");
                frame.showPlace1 = false;
            }
            else
            {
                pushPlace1.setText("Hide Place2");
                frame.showPlace1 = true;
            }
        }