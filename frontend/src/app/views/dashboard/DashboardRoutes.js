import Loadable from 'app/components/Loadable';
import { lazy } from 'react';
import { authRoles } from '../../auth/authRoles';
import PublicationUser from '../publication/PublicationUser';
import AppPage from '../Page/AppPage';
import AppPublicite from '../Publicite/AppPublicite';

const Analytics = Loadable(lazy(() => import('./Analytics')));

const dashboardRoutes = [
  { path: '/dashboard/default', element: <Analytics />, auth: authRoles.admin },
  { path: '/pub', element: <PublicationUser /> },
  { path: '/Page', element: <AppPage/> },
  { path: '/Publicite', element: <AppPublicite/> },
];

export default dashboardRoutes;
