import type {
  ContractType,
  EmployeeOnboardingPhase,
  EmployeeStatus,
  ImssSalaryType,
  ImssWorkerType,
  WorkShift,
} from './employee.enums';

/** POST /api/v1/employees — registro en onboarding (borrador o pendiente de contrato). */
export interface RegisterEmployeeRequest {
  name: string;
  email: string;
  phone: string;
  address: string;
  curp: string;
  rfc: string;
  nss: string;
  clabe: string;
  employeeNumber: string;
  position: string;
  department: string;
  contractType: ContractType;
  workShift: WorkShift;
  salaryPerWeek: number;
  /** ISO date (yyyy-MM-dd). */
  birthDate: string;
  onboardingPhase: EmployeeOnboardingPhase;
}

/** PUT /api/v1/employees/:id */
export interface UpdateEmployeeRequest {
  name: string;
  email: string;
  phone: string;
  address: string;
  curp: string;
  rfc: string;
  nss: string;
  clabe: string;
  position: string;
  department: string;
  contractType: ContractType;
  workShift: WorkShift;
  salaryPerWeek: number;
  bonuses: number;
  foodVouchers: number;
  integrationFactor: number;
}

/** PUT …/promote, …/demote */
export interface ChangePositionRequest {
  position: string;
}

/** GET /api/v1/employees/:id */
export interface EmployeeResponse {
  id: number;
  name: string;
  email: string;
  phone: string;
  address: string;
  birthDate: string;
  nationality: string;
  curp: string;
  rfc: string;
  nss: string;
  clabe: string;
  employeeNumber: string;
  position: string;
  department: string;
  contractType: ContractType;
  workShift: WorkShift;
  hireDate: string | null;
  terminationDate: string | null;
  status: EmployeeStatus;
  salaryPerWeek: number;
  bonuses: number;
  foodVouchers: number;
  integrationFactor: number;
  imssWorkerType: ImssWorkerType;
  imssSalaryType: ImssSalaryType;
  christmasBonusDays: number;
  vacationDays: number;
  vacationPremiumPercent: number;
  createdAt: string;
  updatedAt: string;
}

/** GET /api/v1/employees (search, active) */
export interface EmployeeListItemResponse {
  id: number;
  name: string;
  email: string;
  department: string;
  position: string;
  status: EmployeeStatus;
  hireDate: string | null;
}

/** GET /api/v1/employees/summary */
export interface DepartmentHeadcountResponse {
  department: string;
  headcount: number;
}

export interface EmployeeSummaryResponse {
  totalNotDeleted: number;
  headcountByDepartment: DepartmentHeadcountResponse[];
}

/** GET /api/v1/employees/statistics */
export interface EmployeeStatisticsResponse {
  total: number;
  active: number;
  notActive: number;
}
