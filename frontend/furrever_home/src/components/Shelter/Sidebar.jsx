import {
  Cog6ToothIcon,
  InboxIcon,
  PowerIcon,
  UserCircleIcon
} from "@heroicons/react/24/solid";
import {
  Card,
  List,
  ListItem,
  ListItemPrefix,
  Typography
} from "@material-tailwind/react";
import React from "react";
import { Link } from "react-router-dom";

const Sidebar = () => {
  const [open, setOpen] = React.useState(0);

  const handleOpen = (value) => {
    setOpen(open === value ? 0 : value);
  };

  return (
    <Card className="lg:h-[calc(100vh-2rem)] sm:w-full  lg:max-w-[20rem] m-2 p-4 bg-primary shadow-xl shadow-white-900/5 ">
      {/* <div className="mb-2 p-4">
          <Typography variant="h5" color="white">
            Sidebar
          </Typography>
        </div> */}
      <List>
        {/* <Accordion
            open={open === 1}
            icon={
              <ChevronDownIcon
                strokeWidth={2.5}
                className={`mx-auto h-4 w-4 transition-transform ${open === 1 ? "rotate-180" : ""}`}
              />
            }
          > */}
        {/* <ListItem className="p-0" selected={open === 1}>
              <AccordionHeader onClick={() => handleOpen(1)} className="border-b-0 p-3">
                <ListItemPrefix>
                  <PresentationChartBarIcon className="h-5 w-5" />
                </ListItemPrefix>
                <Typography color="white" className="mr-auto font-normal">
                  Dashboard
                </Typography>
              </AccordionHeader>
            </ListItem> */}
        {/* <AccordionBody className="py-1">
              <List className="p-0">
                <ListItem>
                  <ListItemPrefix>
                    <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                  </ListItemPrefix>
                  Analytics
                </ListItem>
                <ListItem>
                  <ListItemPrefix>
                    <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                  </ListItemPrefix>
                  Reporting
                </ListItem>
                <ListItem>
                  <ListItemPrefix>
                    <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                  </ListItemPrefix>
                  Projects
                </ListItem>
              </List>
            </AccordionBody> */}
        {/* </Accordion> */}
        {/* <Accordion
            open={open === 2}
            icon={
              <ChevronDownIcon
                strokeWidth={2.5}
                className={`mx-auto h-4 w-4 transition-transform ${open === 2 ? "rotate-180" : ""}`}
              />
            }
          >
            <ListItem className="p-0" selected={open === 2}>
              <AccordionHeader onClick={() => handleOpen(2)} className="border-b-0 p-3">
                <ListItemPrefix>
                  <ShoppingBagIcon className="h-5 w-5" />
                </ListItemPrefix>
                <Typography color="white" className="mr-auto font-normal">
                  E-Commerce
                </Typography>
              </AccordionHeader>
            </ListItem>
            <AccordionBody className="py-1">
              <List className="p-0">
                <ListItem>
                  <ListItemPrefix>
                    <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                  </ListItemPrefix>
                  Orders
                </ListItem>
                <ListItem>
                  <ListItemPrefix>
                    <ChevronRightIcon strokeWidth={3} className="h-3 w-5" />
                  </ListItemPrefix>
                  Products
                </ListItem>
              </List>
            </AccordionBody>
          </Accordion> */}
        {/* <hr className="border-white-50" /> */}
        <ListItem>
          <ListItemPrefix>
            <InboxIcon className="h-5 w-5 text-white" />
          </ListItemPrefix>
          <Typography variant="h8" color="white">
            Profile
          </Typography>

        </ListItem>
        <ListItem>
          <ListItemPrefix>
            <UserCircleIcon className="h-5 w-5 text-white" />
          </ListItemPrefix>
          <Typography variant="h8" color="white">
            Pets
          </Typography>
        </ListItem>
        <ListItem>
          <ListItemPrefix>
            <Cog6ToothIcon className="h-5 w-5 text-white" />
          </ListItemPrefix>
          <Typography variant="h8" color="white">
            <Link to="#id">Sidebar</Link>
          </Typography>
        </ListItem>
        <ListItem>
          <ListItemPrefix>
            <PowerIcon className="h-5 w-5 text-white" />
          </ListItemPrefix>
          <Typography variant="h8" color="white">
            <Link to="/shelter/home/#pets">
              Sidebar
            </Link>

          </Typography>
        </ListItem>
      </List>
    </Card>
  );
}

export default Sidebar